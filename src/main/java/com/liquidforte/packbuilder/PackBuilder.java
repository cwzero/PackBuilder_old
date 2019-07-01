package com.liquidforte.packbuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

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

		for (CurseFile file : input.getFiles()) {
			output.getFiles().add(curseClient.update(file));
		}

		return output;
	}

	public void updateManifest(InputStream input, OutputStream output) throws IOException {
		objectMapper.writerFor(Modpack.class).writeValue(output, updateModpack(loadModpack(input)));
	}

	public void downloadModpack(InputStream input, Path modsDir) throws IOException {
		Modpack modpack = loadModpack(input);

		for (CurseFile file : modpack.getFiles()) {
			curseClient.download(file, modsDir);
		}
	}
}
