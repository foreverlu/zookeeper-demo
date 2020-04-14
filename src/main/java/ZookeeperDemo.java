import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import javax.xml.bind.SchemaOutputResolver;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by caoweiwei on 2020/2/24.
 */
public class ZookeeperDemo implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        /**
         * Zookeeper各个参数的意义
         * connct
         */
        ZooKeeper zooKeeper = new ZooKeeper("172.17.12.15:2181", 5000, new ZookeeperDemo());
        System.out.println(zooKeeper.getState());
        connectedSemaphore.await();
        System.out.println("finish");

    }


    public void process(WatchedEvent watchedEvent) {
        System.out.println("watch event :"+ watchedEvent);
        if(watchedEvent.getState() == KeeperState.SyncConnected){
            connectedSemaphore.countDown();
        }
    }
}
