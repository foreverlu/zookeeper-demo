import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by caoweiwei on 2020/2/24.
 */
public class CreateNodeDemo implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        /**
         * Zookeeper各个参数的意义
         * connct
         */
        ZooKeeper zooKeeper = new ZooKeeper("172.17.12.15:2181", 5000, new ZookeeperDemo());
        System.out.println(zooKeeper.getState());
        connectedSemaphore.await();
        String s = zooKeeper.create("/zk-demo/demo", "test".getBytes(), Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        System.out.println(s);
        System.out.println("finish");
        Thread.sleep(Integer.MAX_VALUE);
    }


    public void process(WatchedEvent watchedEvent) {
        System.out.println("watch event :"+ watchedEvent);
        if(watchedEvent.getState() == KeeperState.SyncConnected){
            connectedSemaphore.countDown();
        }
    }
}
