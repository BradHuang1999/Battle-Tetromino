package game;

import java.awt.*;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class TransparentPanel extends JPanel{

    private Color bgColor = new Color(0, 0, 0, 200);

    public TransparentPanel(){
        this.setLayout(null);
        this.setOpaque(false);
        this.setFocusable(true);
    }

    public void paintComponent(Graphics g){
        g.setColor(bgColor);
        Rectangle r = g.getClipBounds();
        g.fillRect(r.x, r.y, r.width, r.height);
        super.paintComponent(g);
    }

    public synchronized void gameOver(String message, int score, int lines, int levels, boolean keyClose, String[][] highest){
        boolean inTopFive = false;

        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(null);

        if (message.equals("You Won!!!")){
            bgColor = new Color(120, 10, 30, 200);
        }

        JLabel[] highScoreLabels = new JLabel[5];
        for (int i = 0; i < 5; i++){
            highScoreLabels[i] = new JLabel();
            if (highest[i][0].length() > 7 && highest[i][0].substring(0, 7).equals("//*top*")){
                inTopFive = true;
                highScoreLabels[i].setFont(new Font("Segoe UI", Font.BOLD, 25));
                highest[i][0] = highest[i][0].substring(7);
            } else {
                highScoreLabels[i].setFont(new Font("Segoe UI", Font.PLAIN, 20));
            }
            highScoreLabels[i].setText(highest[i][0] + "    " + highest[i][1]);
            highScoreLabels[i].setForeground(Color.WHITE);
            highScoreLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            highScoreLabels[i].setBounds(0, 440 + 35 * i, this.getWidth(), 25);
            p.add(highScoreLabels[i]);
        }

        JLabel lb;
        if (inTopFive){
            if (message.equals("Game Over")){
                lb = new JLabel("You are in Top Five!");
            } else {
                lb = new JLabel("Opponent is in Top Five!");
            }
            bgColor = new Color(120, 10, 30, 200);
        } else {
            lb = new JLabel(message);
        }
        lb.setHorizontalAlignment(SwingConstants.CENTER);
        lb.setFont(new Font("Segoe UI", Font.BOLD, 60));
        lb.setHorizontalAlignment(SwingConstants.CENTER);
        lb.setForeground(Color.WHITE);
        lb.setBounds(0, 60, this.getWidth(), 80);
        p.add(lb);

        JLabel lb1 = new JLabel("Score : " + score);
        lb1.setHorizontalAlignment(SwingConstants.CENTER);
        lb1.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lb1.setForeground(Color.WHITE);
        lb1.setBounds(0, 180, this.getWidth(), 30);
        p.add(lb1);

        JLabel lb2 = new JLabel("Lines : " + lines);
        lb2.setHorizontalAlignment(SwingConstants.CENTER);
        lb2.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lb2.setForeground(Color.WHITE);
        lb2.setBounds(0, 220, this.getWidth(), 30);
        p.add(lb2);

        JLabel lb3 = new JLabel("Levels : " + levels);
        lb3.setHorizontalAlignment(SwingConstants.CENTER);
        lb3.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lb3.setForeground(Color.WHITE);
        lb3.setBounds(0, 260, this.getWidth(), 30);
        p.add(lb3);

        JLabel lb4 = new JLabel("HIGH SCORES");
        lb4.setHorizontalAlignment(SwingConstants.CENTER);
        lb4.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lb4.setForeground(Color.WHITE);
        lb4.setBounds(0, 385, this.getWidth(), 30);
        p.add(lb4);

        if (keyClose){
            JLabel lb5 = new JLabel("Press any key to close");
            lb5.setHorizontalAlignment(SwingConstants.CENTER);
            lb5.setFont(new Font("Segoe UI", Font.BOLD, 25));
            lb5.setForeground(Color.WHITE);
            lb5.setBounds(0, 660, this.getWidth(), 30);
            p.add(lb5);
        }

        p.setBounds(0, 0, this.getWidth(), this.getHeight());

        add(p, BorderLayout.CENTER);
    }

    public synchronized void countdown(int countdown){
        JLabel label = new JLabel();
        label.setFont(new Font("Lucida sans", Font.BOLD, 100));
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(0, 0, this.getWidth(), this.getHeight());
        label.setForeground(Color.WHITE);
        add(label);

        try {
            while (countdown > 0){
                setRandomBgColor();
                label.setText("" + countdown);
                repaint();
                revalidate();
                countdown--;
                Thread.sleep(1000);
            }
        } catch (Throwable e){
            e.printStackTrace();
        }
    }

    public synchronized void addSmallMessage(String message){
        JLabel lb = new JLabel(message);
        lb.setFont(new Font("Segoe UI", Font.BOLD, 25));
        lb.setForeground(Color.WHITE);
        lb.setVerticalAlignment(SwingConstants.TOP);
        lb.setHorizontalAlignment(SwingConstants.CENTER);

        int x = 0;
        int y = (this.getHeight() / 2) + 10;
        int w = this.getWidth();
        int h = (this.getHeight() / 2) - 10;
        lb.setBounds(x, y, w, h);
        add(lb);
    }

    public synchronized void addCenterMessage(String message){
        JLabel label = new JLabel(message);
        label.setFont(new Font("Segoe UI", Font.BOLD, 70));
        label.setForeground(Color.WHITE);

        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        int x = 0;
        int y = (this.getHeight() - label.getHeight()) / 2 - 100;
        int w = this.getWidth();
        int h = 100;
        label.setBounds(x, y, w, h);
        add(label);
    }

    public void setRandomBgColor(){
        bgColor = new Color((int)Math.round(Math.random() * 128),
                (int)Math.round(Math.random() * 128),
                (int)Math.round(Math.random() * 128),
                200);
    }
}