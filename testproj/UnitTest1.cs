namespace testproj
{
    using System;

    using JetBrains.dotMemoryUnit;

    using NUnit.Framework;

    [TestFixture]
    public class UnitTest1
    {
        [Test]
        public void TestMethod1()
        {
            dotMemory.Check(
                memory =>
                {
                    var str1 = "1";
                    var str2 = "2";
                    var str3 = "3";
                    Assert.LessOrEqual(2, memory.ObjectsCount);
                    Console.WriteLine(str1 + str2 + str3);
                });
        }
    }
}
