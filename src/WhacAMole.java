import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class WhacAMole {
    int boardWidth = 600;
    int boardHeight = 650;

    boolean moleClickable = false;


    JFrame frame = new JFrame("Mario: Whac A Mole");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();


    JButton[] board = new JButton[9];
    ImageIcon moleIcon;
    ImageIcon plantIcon;

    JButton currMoleTile;
    JButton currPlantTile;

    Random random = new Random();
    Timer setMoleTimer;
    Timer setPlantTimer;

    int score = 0;

    WhacAMole(){
       
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());


         textLabel.setFont(new Font("Arial",Font.PLAIN,50));
         textLabel.setHorizontalAlignment(JLabel.CENTER);
         textLabel.setText("Score : "+ Integer.toString(score));
         textLabel.setOpaque(true);


         textPanel.setLayout(new BorderLayout());
         textPanel.add(textLabel);
         frame.add(textPanel,BorderLayout.NORTH);



         boardPanel.setLayout(new GridLayout(3,3));
         frame.add(boardPanel, BorderLayout.CENTER);


        // plantIcon = new ImageIcon(getClass().getResource("./piranha.png"));
        Image plantImg = new ImageIcon(getClass().getResource("/piranha.png")).getImage();
        plantIcon = new ImageIcon(plantImg.getScaledInstance(150, 150,java.awt.Image.SCALE_SMOOTH));

        Image moleImg = new ImageIcon(getClass().getResource("/monty.png")).getImage();
        moleIcon = new ImageIcon(moleImg.getScaledInstance(150, 150,java.awt.Image.SCALE_SMOOTH));
        
        
        for(int i = 0 ; i < 9 ; i++)
         {
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            tile.setFocusable(false);
          //  tile.setIcon(moleIcon);


            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    JButton tile = (JButton) e.getSource();

                    if (tile == currMoleTile && moleClickable) {
                        score += 10;
                        textLabel.setText("Score : "+ Integer.toString(score));
                        moleClickable = false;
                        adjustSpeed();
                    }

                    else if ( tile == currPlantTile) {
                        textLabel.setText("Game Over : "+ Integer.toString(score));
                        
                        setMoleTimer.stop();
                        setPlantTimer.stop();

                        for( int i = 0; i < 9; i++){
                           board[i].setEnabled(false);   
                        }
                    }
                }
            });
         }
         

         setMoleTimer = new Timer(1000,new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (currMoleTile != null) {
                    currMoleTile.setIcon(null);
                    currMoleTile = null;
                }

                int num = random.nextInt(9); // 0 - 8
                JButton tile = board[num];

                if (currPlantTile == tile) return;

                currMoleTile = tile;
                currMoleTile.setIcon(moleIcon);
                moleClickable = true;
            }
         });


         
         setPlantTimer = new Timer(1500,new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (currPlantTile != null) {
                    currPlantTile.setIcon(null);
                    currPlantTile = null;
                }

               java.util.List<JButton> availableTiles = new java.util.ArrayList<>();
                for (JButton tile : board) {
                    if (tile != currMoleTile) {
                        availableTiles.add(tile);
                    }
                }

                if (availableTiles.isEmpty()) return;

                JButton tile = availableTiles.get(random.nextInt(availableTiles.size()));
                currPlantTile = tile;
                currPlantTile.setIcon(plantIcon);
                    }
         });


         setMoleTimer.start();

         setPlantTimer.start();

         frame.setVisible(true);

    }

        void adjustSpeed() {
            int moleBaseDelay = 1000; 
            int plantBaseDelay = 1500;

            int delayReduction = score / 10;

            int newMoleDelay = Math.max(300, moleBaseDelay - delayReduction);
            int newPlantDelay = Math.max(500, plantBaseDelay - delayReduction);

            setMoleTimer.setDelay(newMoleDelay);
            setPlantTimer.setDelay(newPlantDelay);
        }
}
