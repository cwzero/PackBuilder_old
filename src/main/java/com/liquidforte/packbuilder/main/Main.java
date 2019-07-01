package com.liquidforte.packbuilder.main;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.liquidforte.packbuilder.PackBuilder;
import com.liquidforte.packbuilder.inject.PackBuilderModule;

public class Main {
	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new PackBuilderModule());
		PackBuilder packBuilder = injector.getInstance(PackBuilder.class);

		InputStream input = Main.class.getClassLoader().getResourceAsStream("update.json");

		try {
			packBuilder.downloadModpack(input, Paths.get("mods"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
