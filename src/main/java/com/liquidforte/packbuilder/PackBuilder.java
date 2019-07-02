package com.liquidforte.packbuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.liquidforte.packbuilder.curse.CurseClient;
import com.liquidforte.packbuilder.curse.data.CurseFile;
import com.liquidforte.packbuilder.curse.data.Modpack;

public class PackBuilder {
	private CurseClient curseClient;
	private ObjectMapper objectMapper;

	@Inject
	public PackBuilder(CurseClient curseClient, ObjectMapper objectMapper) {
		this.curseClient = curseClient;
		this.objectMapper = objectMapper;
	}

	public Modpack loadModpack(InputStream input) throws IOException {
		return objectMapper.readerFor(Modpack.class).readValue(input);
	}

	public Modpack updateModpack(Modpack input) throws IOException {
		Modpack output = new Modpack();

		Executor exec = Executors.newCachedThreadPool();

		for (CurseFile file : input.getFiles()) {
			exec.execute(() -> {
				CurseFile result;
				try {
					result = curseClient.update(file);
					if (result.getFileId() != file.getFileId()) {
						System.out.println("Updated " + result.getName());
					}
					output.getFiles().add(result);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
			});
		}

		return output;
	}

	public void updateManifest(InputStream input, OutputStream output) throws IOException {
		objectMapper.writerFor(Modpack.class).writeValue(output, updateModpack(loadModpack(input)));
	}

	public void downloadModpack(InputStream input, Path modsDir) throws IOException {
		if (!modsDir.toFile().exists() || !modsDir.toFile().isDirectory()) {
			modsDir.toFile().mkdirs();
		}
		
		Modpack modpack = loadModpack(input);

		Executor exec = Executors.newCachedThreadPool();

		for (CurseFile file : modpack.getFiles()) {
			//exec.execute(() -> {
				try {
					curseClient.download(file, modsDir);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
			//});
		}
	}
}
