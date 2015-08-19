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
