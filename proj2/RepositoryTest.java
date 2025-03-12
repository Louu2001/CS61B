import gitlet.*;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;


public class RepositoryTest {

    private static final String TEST_FILE = "testfile.txt";
    private static final String TEST_CONTENT = "Hello, Gitlet!";

//    @BeforeEach
//    public void setUp() {
//        // 在每个测试之前初始化 Repository
//        Repository.init();
//
//        // 创建一个测试文件
//        Utils.writeContents(new File(TEST_FILE), TEST_CONTENT);
//    }

    @Test
    public void testInit() {
        // 初始化仓库
        Repository.init();

        // 检查 .gitlet 目录是否存在
        File gitletDir = new File(".gitlet");
        assertTrue(gitletDir.exists(), ".gitlet directory should exist");

        // 检查 HEAD 文件是否存在
        File headFile = new File(".gitlet/HEAD");
        assertTrue(headFile.exists(), "HEAD file should exist");

        // 检查 master 分支文件是否存在
        File masterBranch = new File(".gitlet/refs/heads/master");
        assertTrue(masterBranch.exists(), "master branch file should exist");

        // 检查初始提交是否存在
//        String commitId = Utils.readContentsAsString(masterBranch);
//        File commitFile = new File(".gitlet/objects/commits/" + commitId);
//        assertTrue(commitFile.exists(), "Initial commit file should exist");
    }

    @Test
    public void testAdd() {
        // 调用 add 方法
        Repository.add(TEST_FILE);

        // 检查暂存区是否包含该文件
        Stage stage = Stage.load();
        assertTrue(stage.getAdditionStage().containsKey(TEST_FILE), "File should be added to the staging area.");

        // 检查 Blob 是否被正确创建
        String blobHash = stage.getAdditionStage().get(TEST_FILE);
        Blob blob = Blob.fromFile(blobHash);
        assertEquals(TEST_CONTENT, blob.getContent(), "Blob content should match the file content.");
    }


    @Test
    public void testCommit(){
        Repository.commit(TEST_FILE);
    }

    @Test
    void testRead(){
        // 假设你已经有了提交的 Blob 哈希值
        String testTxtHash = "aab4f8f949276a62b278de5e16918dcf7f4b6969";
        String testfile2TxtHash = "dfddc65a0003134b30b5370f1a34541a3886e4d0";

// 从哈希值获取对应的 Blob 对象
        Blob testTxtBlob = Blob.fromFile(testTxtHash);
        Blob testfile2TxtBlob = Blob.fromFile(testfile2TxtHash);

// 获取文件内容
        String testTxtContent = testTxtBlob.getContent();
        String testfile2TxtContent = testfile2TxtBlob.getContent();

// 打印文件内容
        System.out.println("Content of test.txt:");
        System.out.println(testTxtContent);
        System.out.println("Content of testfile2.txt:");
        System.out.println(testfile2TxtContent);

    }
}