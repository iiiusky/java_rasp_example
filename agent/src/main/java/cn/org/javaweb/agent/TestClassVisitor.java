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

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;


/**
 * @author sky
 */
public class TestClassVisitor extends ClassVisitor implements Opcodes {

	public TestClassVisitor(ClassVisitor cv) {
		super(Opcodes.ASM5, cv);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

		if ("start".equals(name) && "()Ljava/lang/Process;".equals(desc)) {
			System.out.println(name + "方法的描述符是：" + desc);

			return new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {
				@Override
				public void visitCode() {

					mv.visitVarInsn(ALOAD, 0);
					mv.visitFieldInsn(GETFIELD, "java/lang/ProcessBuilder", "command", "Ljava/util/List;");
					mv.visitMethodInsn(INVOKESTATIC, "cn/org/javaweb/agent/ProcessBuilderHook", "start", "(Ljava/util/List;)Z", false);
					Label l1 = new Label();
					mv.visitLabel(l1);
					super.visitCode();

				}
			};
		}
		return mv;
	}
}


