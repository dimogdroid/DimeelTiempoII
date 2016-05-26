/*
   Copyright 2011 
   
   DIFED

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package dimogdroid.com.api;




public class ErrorData {
     
    private ConnectionException.ConnectionError erroresConexion;
   
    public ErrorData(ConnectionException.ConnectionError erroresConexion) {
        super();
        this.erroresConexion = erroresConexion;
    }

    public ConnectionException.ConnectionError getConnectionError() {
        return erroresConexion;
    }

    public void setConnectionError(ConnectionException.ConnectionError connectionError) {
        this.erroresConexion = connectionError;
    }

}
