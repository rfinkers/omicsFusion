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
package nl.wur.plantbreeding.omicsfusion.excel;

/**
 * Exception which is thrown when there is an error during the
 * Excel sheet validation.
 * @author Richard Finkers
 * @version 1.0
 */
public class DataSheetValidationException extends Exception {

    private static final long serialVersionUID = 20100815L;

    /**
     * Excel sheet validation exception.
     */
    public DataSheetValidationException() {
        super();
    }

    /**
     * Excel sheet validation exception.
     * @param cause
     */
    public DataSheetValidationException(Throwable cause) {
        super(cause);
    }

    /**
     * Excel sheet validation exception.
     * @param message
     * @param cause
     */
    public DataSheetValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Excel sheet validation exception.
     * @param message
     */
    public DataSheetValidationException(String message) {
        super(message);
    }
}
