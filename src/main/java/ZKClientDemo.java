import org.I0Itec.zkclient.ZkClient;

/**
 * Created by caoweiwei on 2020/2/24.
 */
public class ZKClientDemo {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("172.17.12.15:2181",5000);
        String path = "/zk-book/zkclient-c1";
        zkClient.createPersistent(path);
    }

}
