import java.io.File;
import java.util.Arrays;
import java.util.List;

public class retrieve {

    public static List<File> assemble(File one)
    {

        String tmpName = one.getName();//{name}.{number}
        String destFileName = tmpName.substring(0, tmpName.lastIndexOf('.'));
        File[] files = one.getParentFile().listFiles(
                (File dir, String name) -> name.matches(destFileName + "[.]\\d+"));
        Arrays.sort(files);
        return Arrays.asList(files);
    }
}
