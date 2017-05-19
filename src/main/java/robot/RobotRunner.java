package robot;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import static java.awt.event.KeyEvent.*;

/**
 * 使用awt实现的
 * Created by tend on 2017/5/17.
 */
public class RobotRunner {

    private static final Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    //鼠标单击,要双击就连续调用
    private static void pressMouse(Robot r, int m, int delay) {
        r.mousePress(m);
        r.delay(10);
        r.mouseRelease(m);
        r.delay(delay);
    }

    // 逐个按键
    private static void pressKeys(Robot r, int[] ks, int delay) {
        for (int i = 0; i < ks.length; i++) {
            r.keyPress(ks[i]);
            r.delay(10);
            r.keyRelease(ks[i]);
            r.delay(delay);
        }
    }

    // 组合键
    private static void compositeKeyPress(int[] ks) {
        for (int k : ks) {
            robot.keyPress(k);
        }
        robot.delay(200);
        for (int k : ks) {
            robot.keyRelease(k);
        }
        robot.delay(500);
    }

    private static void setClip(String data) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = new StringSelection(data);
        clipboard.setContents(transferable, null);
    }

    private static String getClip() throws IOException, UnsupportedFlavorException {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        return (String) clipboard.getContents(null).getTransferData(DataFlavor.stringFlavor);
    }

    public static void main(String[] args) throws Exception {
//        demo1_回车();
//        demo2_点击();
//        demo3_执行cmd();
//        demo4_右键();

        compositeKeyPress(new int[]{VK_ALT, VK_TAB});

        compositeKeyPress(new int[]{VK_CONTROL, VK_L});
        setClip("www.baidu.com");
        compositeKeyPress(new int[]{VK_CONTROL, VK_V});
        compositeKeyPress(new int[]{VK_ENTER});

        compositeKeyPress(new int[]{VK_CONTROL, VK_F});
        setClip("学术");
        compositeKeyPress(new int[]{VK_CONTROL, VK_V});
        compositeKeyPress(new int[]{VK_ESCAPE});
        compositeKeyPress(new int[]{VK_ENTER});
        robot.delay(1000);

        compositeKeyPress(new int[]{VK_CONTROL, VK_U});
        compositeKeyPress(new int[]{VK_CONTROL, VK_A});
        compositeKeyPress(new int[]{VK_CONTROL, VK_C});
        compositeKeyPress(new int[]{VK_ALT, VK_TAB});
        String html = getClip();
        System.out.println(html);
    }

    private static void demo4_右键() {
        //右键测试
        int y = Toolkit.getDefaultToolkit().getScreenSize().height - 10;
        int x = Toolkit.getDefaultToolkit().getScreenSize().width - 60;
        robot.mouseMove(x, y);

        //如果是双键鼠标,请改用InputEvent.BUTTON2_MASK试试,我没有这种鼠标
        pressMouse(robot, InputEvent.BUTTON3_DOWN_MASK, 500);
        //显示日期调整对话框 a
        pressKeys(robot, new int[]{KeyEvent.VK_A}, 1000);
        robot.delay(2000);
        pressKeys(robot, new int[]{KeyEvent.VK_ESCAPE}, 0);
        robot.delay(1000);
        new Thread(() -> {
            robot.delay(1000);
            //回车
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }).start();
    }

    private static void demo3_执行cmd() {
        //运行CMD命令
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_R);
        robot.keyRelease(KeyEvent.VK_WINDOWS);
        robot.keyRelease(KeyEvent.VK_R);
        robot.delay(500);

        pressKeys(robot, new int[]{KeyEvent.VK_C, KeyEvent.VK_M, KeyEvent.VK_D, KeyEvent.VK_ENTER,}, 500);
        robot.mouseMove(400, 400);
        robot.delay(500);
        //运行DIR命令  dir enter
        pressKeys(robot, new int[]{KeyEvent.VK_D, KeyEvent.VK_I, KeyEvent.VK_R, KeyEvent.VK_ENTER}, 500);
        robot.delay(1000);
        //运行CLS命令 cls enter
        pressKeys(robot, new int[]{KeyEvent.VK_C, KeyEvent.VK_L, KeyEvent.VK_S, KeyEvent.VK_ENTER}, 500);
        robot.delay(1000);
        //运行EXIT命令 exit enter
        pressKeys(robot, new int[]{KeyEvent.VK_E, KeyEvent.VK_X, KeyEvent.VK_I, KeyEvent.VK_T, KeyEvent.VK_ENTER}, 500);
        robot.delay(1000);
    }

    private static void demo2_点击() {
        //设置开始菜单的大概位置
        int x = 40;
        int y = Toolkit.getDefaultToolkit().getScreenSize().height - 10;

        //鼠标移动到开始菜单,
        robot.mouseMove(x, y);
        robot.delay(500);

        //单击三次开始菜单
        for (int i = 0; i < 3; i++)
            pressMouse(robot, InputEvent.BUTTON1_MASK, 500);
        robot.delay(1000);
    }

    private static void demo1_回车() throws AWTException {
        robot.delay(2000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }


}
