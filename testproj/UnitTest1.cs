/*
 * Copyright 2000-2023 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

namespace testproj
{
    using System;
    using System.Collections.Generic;

    using JetBrains.dotMemoryUnit;

    using NUnit.Framework;

    [TestFixture]
    public class UnitTest1
    {
        [Test]
        public void TestMethod1()
        {
            var strs = new List<string>();
            var memoryCheckPoint = dotMemory.Check();
            strs.Add(GenStr());
            strs.Add(GenStr());
            strs.Add(GenStr());

            dotMemory.Check(
                memory =>
                {
                    var strCount = memory
                        .GetDifference(memoryCheckPoint)
                        .GetNewObjects()
                        .GetObjects(i => i.Type == typeof(string))
                        .ObjectsCount;

                    Assert.LessOrEqual(strCount, 2);                    
                });

            strs.Clear();
        }

        [Test]
        public void TestMethod2()
        {
            var strs = new List<string>();
            var memoryCheckPoint = dotMemory.Check();
            strs.Add(GenStr());
            strs.Add(GenStr());

            dotMemory.Check(
                memory =>
                {
                    var strCount = memory
                        .GetDifference(memoryCheckPoint)
                        .GetNewObjects()
                        .GetObjects(i => i.Type == typeof(string))
                        .ObjectsCount;

                    Assert.LessOrEqual(strCount, 2);
                });

            strs.Clear();
        }

        private static string GenStr()
        {
            return Guid.NewGuid().ToString();
        }
    }
}
