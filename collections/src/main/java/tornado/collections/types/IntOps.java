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
package tornado.collections.types;

public class IntOps {
	public static final float EPSILON = 1e-7f;
	public static final String fmt = "%d";
	public static final String fmt2 = "{%d,%d}";
	public static final String fmt3 = "{%d,%d,%d}";
	public static String	fmt6= "{%d,%d,%d,%d,%d,%d}";
	
	public static final boolean compare(float a, float b){
		return (a == b);
	}
}
