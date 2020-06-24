package no.unit.transformer.features;

import java.io.File;

public class TestWiring {
    protected File getFileFromResources(String filename) {
        String file = ClassLoader.getSystemResource(filename).getFile();
        return new File(file);
    }

}
