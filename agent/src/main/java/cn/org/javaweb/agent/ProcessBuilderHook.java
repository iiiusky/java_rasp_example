/*
 * Copyright sky 2019-04-04 Email:sky@03sec.com.
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
package cn.org.javaweb.agent;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author sky
 */
public class ProcessBuilderHook {

	public static boolean start(List<String> commands) {
		String[] commandArr = commands.toArray(new String[commands.size()]);
		System.out.println(Arrays.toString(commandArr));
		ClassUtils.printStackTrace();
		return false;

	}
}
