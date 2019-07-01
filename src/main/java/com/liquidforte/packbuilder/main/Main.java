package com.liquidforte.packbuilder.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.liquidforte.packbuilder.PackBuilder;
import com.liquidforte.packbuilder.inject.PackBuilderModule;

public class Main {
	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new PackBuilderModule());
		PackBuilder packBuilder = injector.getInstance(PackBuilder.class);

		String mode = args[0].trim().toLowerCase();
		Path manifest = Paths.get(args[1]);
		Path target = Paths.get(args[2]);

		try {
			if (mode.equals("update")) {
				packBuilder.updateManifest(Files.newInputStream(manifest), Files.newOutputStream(target));
			} else if (mode.equals("download")) {
				packBuilder.downloadModpack(Files.newInputStream(manifest), target);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
