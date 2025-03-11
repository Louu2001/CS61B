# Gitlet Design Document

**Name**:

## Classes and Data Structures

### Class 1

#### Fields

1. Field 1
2. Field 2


### Class 2

#### Fields

1. Field 1
2. Field 2


## Algorithms

## Persistence



# Gitlet Design Document

**Name**: Lou

## Classes and Data Structures

### Repository
#### Fields
1. `CWD` - 当前工作目录。
2. `GITLET_DIR` - `.gitlet` 版本控制主目录。
3. `OBJECTS_DIR` - 存放所有对象（`blobs`、`trees`、`commits`）。
4. `STAGING_AREA` - 暂存区。
5. `HEAD_FILE` - 记录当前分支。
6. `currCommit` - 当前提交。

### Commit
#### Fields
1. `message` - 提交信息。
2. `timestamp` - 时间戳。
3. `parent` - 父提交的哈希值。
4. `tree` - 该提交的文件树。

### Blob
#### Fields
1. `content` - 文件内容。
2. `hash` - 该 blob 的 SHA-1 哈希值。

### Tree
#### Fields
1. `fileTree` - 记录文件名到 `blobHash` 的映射。

### Stage
#### Fields
1. `additionStage` - 记录已添加但未提交的文件（`fileName -> blobHash`）。
2. `removalStage` - 记录被标记删除的文件。

### Branch
#### Fields
1. `name` - 分支名称。
2. `commitId` - 当前分支指向的提交。

## Algorithms
1. **init** - 创建 `.gitlet` 目录和默认分支。
2. **add** - 计算文件哈希，存入 `Blob`，更新 `Stage`。
3. **commit** - 生成 `Tree`，创建 `Commit`，清空 `Stage`。
4. **checkout** - 切换到特定的 `commit` 或 `branch`。
5. **log** - 显示提交历史。
6. **rm** - 将文件从暂存区或当前版本中删除。

## Persistence
1. 使用 `Utils.writeObject()` 和 `Utils.readObject()` 序列化对象。
2. `Commit`、`Blob`、`Tree` 存入 `.gitlet/objects/`。
3. `Stage` 存入 `.gitlet/staging_area/`。
4. `Branch` 记录在 `.gitlet/refs/heads/`。
5. `HEAD` 存储当前分支位置。

