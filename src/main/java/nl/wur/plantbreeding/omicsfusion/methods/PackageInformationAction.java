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
package nl.wur.plantbreeding.omicsfusion.methods;

import com.opensymphony.xwork2.ActionSupport;
import java.util.logging.Logger;

/**
 * Show the currently installed R and R packages versions.
 * @author Richrad Finkers
 * @version 1.0
 */
public class PackageInformationAction extends ActionSupport {

    /** Serial version UID. */
    private static final long serialVersionUID = 240910L;
    /** The logger. */
    private static final Logger LOG =
            Logger.getLogger(PackageInformationAction.class.getName());
}
