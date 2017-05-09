/* 
 * Copyright 2012 James Clarkson.
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
package tornado.common;


public class DeviceObjectState {
	private boolean valid;
	private boolean modified;
	private boolean contents;
	
	private ObjectBuffer buffer;
	
	public DeviceObjectState(){
		valid = false;
		modified = false;
		contents = false;
		buffer = null;
	}
	
	public void setBuffer(ObjectBuffer value){
		buffer = value;
	}
	
	public boolean hasBuffer(){
		return buffer != null;
	}
	
	public ObjectBuffer getBuffer(){
		return buffer;
	}
	
	public boolean isValid(){
		return valid;
	}
	
	public boolean isModified(){
		return modified;
	}
	
	public void invalidate(){
		valid = false;
	}
	
	public boolean hasContents(){
		return contents;
	}
	
	public void setContents(boolean value){
		contents = value;
	}
	
        @Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append((isValid()) ? "V" : "-");
		sb.append((isModified()) ? "M" : "-");
		sb.append((hasContents()) ? "C" : "-");
		if(hasBuffer()){
		sb.append(String.format(" address=0x%x, size=%s ",buffer.toAbsoluteAddress(),RuntimeUtilities.humanReadableByteCount(buffer.size(), true)));
		} else {
			sb.append(" <unbuffered>");	
		}
		
		return sb.toString();
	}

	public void setModified(boolean value) {
		modified = value;
	}

	public void setValid(boolean value) {
		valid = value;
	}

	public long getAddress() {
		return buffer.toAbsoluteAddress();
	}
        
        public long getOffset() {
		return buffer.toRelativeAddress();
	}
}