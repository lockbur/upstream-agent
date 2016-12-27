package com.jinyinwu.upstream.agent.nginx;

import java.io.*;

public class PidReader {
    private final File pidFile;

    public PidReader(String filename) throws FileNotFoundException {
        File file = new File(filename);

        if (file.exists()) {
            pidFile = file;
        } else {
            throw new FileNotFoundException("Pidfile not found: " + file.getAbsolutePath());
        }
    }

    public int getPid() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(pidFile));
            String line = reader.readLine();

            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            throw new InvalidPidException("Could not parse pid", e);
        } catch (IOException e) {
            throw new InvalidPidException("Could not read pid, " + e.getMessage(), e);
        }
    }
}
