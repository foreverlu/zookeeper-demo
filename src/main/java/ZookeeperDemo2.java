import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by caoweiwei on 2020/4/12. 创建一个复用sessionId和sessionPasswd来创建一个zookeeper对象
 */

public class ZookeeperDemo2 implements Watcher {

    private static CountDownLatch connectedSemphore = new CountDownLatch(1);

    public static void main(String[] args) {
        try {
            ZooKeeper zooKeeper = new ZooKeeper("172.17.12.15:2181", 5000, new ZookeeperDemo2());
            connectedSemphore.await();
            long sessionId = zooKeeper.getSessionId();
            byte[] sessionPasswd = zooKeeper.getSessionPasswd();
            zooKeeper = new ZooKeeper("172.17.12.15:2181", 5000, new ZookeeperDemo2(), sessionId,
                    sessionPasswd);
            zooKeeper = new ZooKeeper("172.17.12.15:2181", 5000, new ZookeeperDemo2(), 1l,
                    "test".getBytes());
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void process(WatchedEvent watchedEvent) {
        System.out.println("recieved watched event: " + watchedEvent);
        if(watchedEvent.getState()== KeeperState.SyncConnected){
            connectedSemphore.countDown();
        }
    }


}