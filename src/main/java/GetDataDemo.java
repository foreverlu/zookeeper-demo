import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * Created by caoweiwei on 2020/4/14.
 */
public class GetDataDemo implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static Stat stat = new Stat();

    public static void main(String[] args) throws Exception {
        /**
         * Zookeeper各个参数的意义
         * connct
         */
        ZooKeeper zooKeeper = new ZooKeeper("172.17.12.15:2181", 5000, new ZookeeperDemo());
        System.out.println(zooKeeper.getState());
        connectedSemaphore.await();
        List<String> children = zooKeeper.getChildren("/zk-demo", true);
        System.out.println(children);

        byte[] data = zooKeeper.getData("zk-demo", true, stat);
        System.out.println(new String(data));

        Thread.sleep(Integer.MAX_VALUE);
    }


    public void process(WatchedEvent watchedEvent) {
        System.out.println("watch event :" + watchedEvent);
        if (watchedEvent.getState() == KeeperState.SyncConnected) {
            connectedSemaphore.countDown();
        }
    }
}
