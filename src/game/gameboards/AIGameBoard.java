package game.gameboards;

import game.Tetromino;

/**
 * @author Brad Huang
 * @date Jan 18, 2016
 */
public class AIGameBoard extends GameBoard{
    public boolean hasUnhandledTetromino = false;

    int[][][] chart = new int[12][4][3];
        /* array explanation:
            [15][4] represent the maximum 60 possibilities of the tetromino could be arranged
            - [0] is the x coordinate of left column of the tetromino
            - [1] is the y coordinate of the bottom of the tetromino it is going to end up, 99999 if arrangement not possible
            - [2] is the spikiness of such arrangement, 99999 if arrangement not possible
         */

    /**
     * move the movables to the best location
     * @param hold hold piece
     */
    public void moveToBestPosition(Tetromino hold) {
        hasUnhandledTetromino = false;

        int xToMove, numToFlip;
        int[] currentBestPosition, holdBestPosition;

        // calculate best positions
        currentBestPosition = findCurrentTetrominoBestPosition(this.current);
        holdBestPosition = findCurrentTetrominoBestPosition(hold);

        if (holdBestPosition[3] < currentBestPosition[3]) {
            holdSwitch = true;
            xToMove = holdBestPosition[0] - this.piecePositionX;
            numToFlip = holdBestPosition[2] >= this.current.getPhase() ? holdBestPosition[2] - this.current.getPhase() : 4 + holdBestPosition[2] - this.current.getPhase();

        } else {
            xToMove = currentBestPosition[0] - this.piecePositionX;
            numToFlip = currentBestPosition[2] >= this.current.getPhase() ? currentBestPosition[2] - this.current.getPhase() : 4 + currentBestPosition[2] - this.current.getPhase();
        }

        // deploy the pieces
        new Thread(() -> {
            for (int i = 0; i < Math.abs(xToMove); i++) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                }
                this.move(xToMove / Math.abs(xToMove));
            }
        }, "AI-HorizonalMove Thread").start();

        new Thread(() -> {
            for (int i = 0; i < Math.abs(numToFlip); i++) {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                }
                this.rotate();
            }
        }, "AI-Flip Thread").start();

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
        }
    }

    /**
     * calculate the positions
     * @param tetromino tetromino to be placed
     * @return the x, y, phase and spikiness of the best position in an array
     */
    private int[] findCurrentTetrominoBestPosition(Tetromino tetromino) {
        boolean[][] mapCopy;

        for (int i = 1; i < 13; i++) {
            for (int phase = 0; phase < 4; phase++) {

                chart[i - 1][phase][0] = i;

                mapCopy = getMapWithTetromino(tetromino, phase, i);

                if (mapCopy == null) {
                    chart[i - 1][phase][1] = 99999;
                    chart[i - 1][phase][2] = 99999;
                } else {
                    chart[i - 1][phase][2] = getSpikiness(mapCopy);
                }
            }
        }

        int bestX = 99999, bestPosition = 99999, bestY = 99999, bestSpikiness = 99999;
        for (int i = 0; i < 12; i++) {
            for (int phase = 0; phase < 4; phase++) {
                if ((chart[i][phase][2] < bestSpikiness) || (chart[i][phase][2] == bestSpikiness && chart[i][phase][1] < bestY)) {
                    bestX = chart[i][phase][0];
                    bestY = chart[i][phase][1];
                    bestSpikiness = chart[i][phase][2];
                    bestPosition = phase;
                }
            }
        }
        return new int[]{bestX, bestY, bestPosition, bestSpikiness};
    }

    /**
     * get the map with the tetromino for calculation
     * @param tetromino tetromino to be placed
     * @param phase phase for the tetromino
     * @param xCoordinate xcoordinate or the tetromino
     * @return the map with tetromino with it, or null if mapping not possible
     */
    private boolean[][] getMapWithTetromino(Tetromino tetromino, int phase, int xCoordinate) {
        boolean[][] shape, mapCopy;
        int YCoordinate;
        boolean exceed = false;

        shape = tetromino.getShapes()[phase];
        mapCopy = new boolean[14][25];
        for (int a = 0; a < 14; a++) {
            for (int b = 0; b < 25; b++) {
                if (this.movable[a][b]) {
                    mapCopy[a][b] = false;
                } else {
                    mapCopy[a][b] = this.map[a][b];     // generate map array without the movable
                }
            }
        }

        YCoordinate = 1;
        outerLoop:
        for (int a = 21; a > 0; a--) {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    if (shape[x][y] && mapCopy[xCoordinate + x][a + y - 1]) {       // find y coordinate
                        YCoordinate = a;
                        break outerLoop;
                    }
                }
            }
        }

        chart[xCoordinate - 1][phase][1] = YCoordinate;

        outerLoop:
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (shape[x][y]) {
                    if ((xCoordinate + x) > 12) {
                        exceed = true;
                        break outerLoop;
                    } else {
                        mapCopy[xCoordinate + x][YCoordinate + y] = true;      // add tetromino
                    }
                }
            }
        }

        if (exceed) {
            return null;    // if not possible to map, return null
        } else {
            return mapCopy;     // return new map
        }
    }

    /**
     * calculate the spikiness of the map with Genetic Programming data based on 4 heuristics
     * @param map the map to be calculated
     * @return the spikiness
     */
    public int getSpikiness(boolean[][] map) {
        return (int) Math.round(51.0066 * getAggHeight(map) - 76.0666 * Math.pow(getCompleteLines(map), 2) + 35.663 * getHoleNum(map) + 18.4483 * getBumpiness(map));
    }

    /**
     * calculate huristic one: aggregate height
     * @param map map to be calculated
     * @return all height added together
     */
    private int getAggHeight(boolean[][] map) {
        int height = 0;
        for (int i = 1; i < 13; i++) {
            height += getHeight(map, i);
        }
        return height;
    }

    /**
     * calculate heuristic two: complete lines - the one to maximize
     * @param map map to be calculated
     * @return lines completed
     */
    private int getCompleteLines(boolean[][] map) {
        int rowDisappered = 0;
        boolean disappear;
        for (int i = 1; i < 21; i++) {
            disappear = true;
            for (int j = 1; j < 13; j++) {
                if (!map[j][i]) {
                    disappear = false;
                    break;
                }
            }

            if (disappear) {
                rowDisappered++;
            }
        }
        return rowDisappered;
    }

    /**
     * calculate heuristic three: hole numbers in the map
     * @param map map to be calculated
     * @return the hole numbers
     */
    public int getHoleNum(boolean[][] map) {
        int holeNum = 0;
        for (int i = 1; i < 21; i++) {
            for (int j = 1; j < 13; j++) {
                if ((!map[j][i]) && map[j - 1][i] && map[j + 1][i] && map[j][i + 1] && map[j][i - 1]) {
                    holeNum++;
                }
            }
        }
        return holeNum;
    }

    /**
     * calculate heuristic four: bumpiness of the map - we want the map to be flat
     * @param map map to be calculated
     * @return the bumpiness of the map
     */
    private int getBumpiness(boolean[][] map) {
        int height = 0;
        for (int i = 2; i < 13; i++) {
            height += Math.abs(getHeight(map, i) - getHeight(map, i - 1));
        }
        return height;
    }

    /**
     * update the method newTetromino to fit in AI
     * @param tetromino tetromino to be drawn
     */
    @Override
    public void newTetromino(Tetromino tetromino) {
        hasUnhandledTetromino = true;
        super.newTetromino(tetromino);
    }
}