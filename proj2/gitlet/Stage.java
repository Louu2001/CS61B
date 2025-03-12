package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static gitlet.Utils.*;

public class Stage implements Serializable, Dumpable {
    private final Map<String, String> additionStage; // fileName -> blobHash
    private final Set<String> removalStage; // 记录删除的文件

    @Override
    public void dump() {
        System.out.println("Addition Stage: " + additionStage);
        System.out.println("Removal Stage: " + removalStage);
    }

    private static final File STAGE_FILE = join(Repository.GITLET_DIR, "staging_area", "stage");

    public Stage() {
        additionStage = new HashMap<>();
        removalStage = new HashSet<>();
    }

    // 加载暂存区
    public static Stage load() {
        if (!STAGE_FILE.exists()) return new Stage();
        return readObject(STAGE_FILE, Stage.class);
    }

    // 保存暂存区
    public void save() {
        writeObject(STAGE_FILE, this);
    }

    // 添加文件到暂存区
    public void addToAddition(String fileName, String blobHash) {
        additionStage.put(fileName, blobHash);
        removalStage.remove(fileName); // 取消删除标记
        save();
    }

    // 从暂存区移除文件
    public void removeFromAddition(String fileName) {
        additionStage.remove(fileName);
        save();
    }

    // 记录要删除的文件
    public void markForRemoval(String fileName) {
        removalStage.add(fileName);
        additionStage.remove(fileName);
        save();
    }

    // 获取暂存的文件
    public Map<String, String> getAdditionStage() {
        return additionStage;
    }

    // 获取标记删除的文件
    public Set<String> getRemovalStage() {
        return removalStage;
    }

    // 清空暂存区（提交后调用）
    public void clear() {
        additionStage.clear();
        removalStage.clear();
        save();
    }
}
