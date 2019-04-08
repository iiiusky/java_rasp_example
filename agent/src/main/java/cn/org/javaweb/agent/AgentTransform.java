/*
 * Copyright sky 2019-04-03 Email:sky@03sec.com.
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

import org.apache.commons.io.IOUtils;
import org.objectweb.asm.*;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.regex.Pattern;

/**
 * @author sky
 */
public class AgentTransform implements ClassFileTransformer {

	/**
	 * @param loader
	 * @param className
	 * @param classBeingRedefined
	 * @param protectionDomain
	 * @param classfileBuffer
	 * @return
	 * @throws IllegalClassFormatException
	 */
	@Override
	public byte[] transform(ClassLoader loader, String className,
	                        Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
	                        byte[] classfileBuffer) throws IllegalClassFormatException {

		className = className.replace("/", ".");

		try {
			if (className.contains("ProcessBuilder")) {
				System.out.println("Load class: " + className);

				ClassReader  classReader  = new ClassReader(classfileBuffer);
				ClassWriter  classWriter  = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
				ClassVisitor classVisitor = new TestClassVisitor(classWriter);

				classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);

				classfileBuffer = classWriter.toByteArray();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String regexp = "ProcessBuilder";
		if (Pattern.compile(regexp).matcher(className).find()) {
			try {
				className = className.substring(className.lastIndexOf(".") + 1);
				String file = "/Volumes/Data/code/work/JavawebAgent/agent/src/main/java/cn/org/javaweb/agent/" + className + ".class";
				IOUtils.copy(new ByteArrayInputStream(classfileBuffer), new FileOutputStream(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return classfileBuffer;
	}


}
