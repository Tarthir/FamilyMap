//import server.FamilyMapServerProxy;

/**
 * Created by tyler on 2/20/2017.
 * Runs our junit tests
 */

public class TestDriver {

    public static void main(String[] args) {
        /*if (args.length == 1) {
            FamilyMapServerProxy.SINGLETON.setServerPort(args[0]);
        } else if (args.length == 2)

        {
            FamilyMapServerProxy.SINGLETON.setServerHost(args[0]);
            FamilyMapServerProxy.SINGLETON.setServerPort(args[1]);
        }*/

        org.junit.runner.JUnitCore.main(
                "test.FamilyMapServerProxyTest",
                "test.UserDaoTest",
                "test.AuthTokenDaoTest",
                "test.EventDaoTest",
                "test.PersonDaoTest",
                "test.MultiDaoTest",
                "test.DataGeneratorTest",
                "test.LoadServiceTest",
                "test.EventServiceTest",
                "test.EventsServiceTest",
                "test.LoginServiceTest",
                "test.PeopleServiceTest",
                "test.PersonServiceTest",
                "test.RegisterServiceTest",
                "test.FillServiceTest",
                "test.ClearServiceTest");
    }
}



        /*org.junit.runner.JUnitCore.runClasses(
                UserDaoTest.class,
                AuthTokenDaoTest.class,
                EventDaoTest.class,
                PersonDaoTest.class,
                MultiDaoTest.class,
                DataGeneratorTest.class,
                FamilyMapServerProxyTest.class,
                LoadServiceTest.class,
                EventServiceTest.class,
                EventsServiceTest.class,
                LoginServiceTest.class,
                PeopleServiceTest.class,
                PersonServiceTest.class,
                RegisterServiceTest.class,
                FillServiceTest.class,
                ClearServiceTest.class);*/