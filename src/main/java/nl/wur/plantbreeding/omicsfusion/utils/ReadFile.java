/*
 * Copyright 2011 omicstools.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.wur.plantbreeding.omicsfusion.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard Finkers
 */
public class ReadFile {

    /**
     * The logger.
     */
    private static final Logger LOG = Logger.getLogger(ReadFile.class.getName());

    /**
     * Read the filenames fo the opriginal input excel sheets.
     *
     * @param fileName Name of the file with the names of the response and
     * predictor sheets.
     * @return Names of the sheets.
     * @throws IOException if something witn wring with file IO.
     */
    public String[] ReadSheetFileNames(String fileName) throws IOException {

        File f = new File(fileName);
        //FIXME: be sure to write to the current /tmp directory for this session
        FileInputStream fip = new FileInputStream(f);

        String[] content = null;
        StringBuilder text = new StringBuilder();

        if (f.exists()) {
            String NL = System.getProperty("line.separator");
            Scanner scanner = new Scanner(fip);
            try {
                while (scanner.hasNextLine()) {
                    text.append(scanner.nextLine()).append(NL);
                }
            }
            finally {
                scanner.close();
            }
            content = text.toString().split(NL);
            LOG.info("response: " + content[0] + " Predictor: "
                    + content[1]);

        } else {
            LOG.warning("This file does not exist");
            //TODO throw exception?
        }

        return content;
    }

    /**
     * Read the name of the response from the (R generated) analysis.txt file.
     *
     * @param filename Name of the file.
     * @return Name of the response variable.
     * @throws IOException
     * @deprecated Take name from sqlite database.
     */
    public String ReadResponseName(String filename) throws IOException {
        File f = new File(filename);
        FileInputStream fip = new FileInputStream(f);
        String responseName = null;
        StringBuilder text = new StringBuilder();

        String[] content = null;

        if (f.exists()) {
            String NL = System.getProperty("line.separator");
            Scanner scanner = new Scanner(fip);
            try {
                while (scanner.hasNextLine()) {
                    text.append(scanner.nextLine()).append(NL);
                }
            }
            finally {
                scanner.close();
            }
            String[] lines = text.toString().split(NL);
            content = lines[1].split("\"");

            responseName = content[3];
        } else {
            LOG.log(Level.WARNING, "This file{0} does not exist", filename);
            //TODO throw exception?
        }
        return responseName;
    }
}
