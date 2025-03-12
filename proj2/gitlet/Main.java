package gitlet;

/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @author Lou
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        if (args.length == 0) {
            Utils.error("Must have at least one argument");
            System.exit(-1);
        }

        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                // TODO: handle the `init` command
                validateArgs(args, 1);
                Repository.init();
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                validateArgs(args, 2);
                Repository.add(args[1]);
                break;
            /* * commit command */
            case "commit":
                if (args.length < 2 || args[1].trim().isEmpty()) {
                    System.out.println("Please enter a commit message.");
                    System.exit(0);
                }
                validateArgs(args, 2);

                Repository.commit(args[1]);
                break;
            /* * rm command */
            case "rm":
                validateArgs(args,2);
                Repository.rm(args[1]);
                break;
            // TODO: FILL THE REST IN
            default:
                System.out.println("No command with that name exists.");
                System.exit(0);
        }
    }

    /**
     * 验证命令行参数的数量是否符合预期。
     *
     * @param args           用户输入的参数数组
     * @param expectedLength 期望的参数个数
     */
    private static void validateArgs(String[] args, int expectedLength) {
        if (args.length != expectedLength) {
            Utils.error("Incorrect operands.");
            System.exit(-1);
        }
    }
}
