namespace testproj
{
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
                    Assert.AreEqual(10, memory.ObjectsCount);
                });
        }
    }
}
