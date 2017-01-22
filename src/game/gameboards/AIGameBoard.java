package game.gameboards;

import game.Tetromino;

/**
 * Created by Brad Huang on 1/18/2017.
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

    public void moveToBestPosition(int level, Tetromino hold) {
        hasUnhandledTetromino = false;

        long time0, time1;
        time0 = System.nanoTime();

        int YCoordinate = getHeight(this.map, 1, 13);
        int timeRamaining = (level < 7 ? (500 - level * 40) : (340 / (level - 5)) + 150) * (YCoordinate > 19 ? 1 : (20 - YCoordinate));

        int xToMove, numToFlip;
        int[] currentBestPosition, holdBestPosition;

        currentBestPosition = findCurrentTetrominoBestPosition(this.current);
        holdBestPosition = findCurrentTetrominoBestPosition(hold);

//        System.out.println("current: x = " + currentBestPosition[0] + " \ty = " + currentBestPosition[1] + " \tposition = " + currentBestPosition[2] + " \tspikiness = " + currentBestPosition[3]);
//        System.out.println("   hold: x = " + holdBestPosition[0] + " \ty = " + holdBestPosition[1] + " \tposition = " + holdBestPosition[2] + " \tspikiness = " + holdBestPosition[3]);
//        boolean[][] bestPPP;

        if (holdBestPosition[3] < currentBestPosition[3]) {
            // TODO if fast then sleep
            holdSwitch = true;

            xToMove = holdBestPosition[0] - this.piecePositionX;
            numToFlip = holdBestPosition[2] >= this.current.getPhase() ? holdBestPosition[2] - this.current.getPhase() : 4 + holdBestPosition[2] - this.current.getPhase();

//            ////////////////////TEST
//            System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
//            bestPPP = getMapWithTetromino(hold, holdBestPosition[2], holdBestPosition[0]);
//            for (int b = 20; b > 0; b--) {
//                for (int a = 1; a < 13; a++) {
//                    if (bestPPP[a][b]) {
//                        System.out.print(1);
//                    } else {
//                        System.out.print(0);
//                    }
//                }
//                System.out.println();
//            }
//            System.out.println(
//                    " \taggHeight = " + getAggHeight(bestPPP) +
//                            " \tcompleteLines = " + getCompleteLines(bestPPP) +
//                            " \tholeNum = " + getHoleNum(bestPPP) +
//                            " \tbumpiness = " + getBumpiness(bestPPP) +
//                            " \tspikiness = " + getSpikiness(bestPPP));

        } else {
            xToMove = currentBestPosition[0] - this.piecePositionX;
            numToFlip = currentBestPosition[2] >= this.current.getPhase() ? currentBestPosition[2] - this.current.getPhase() : 4 + currentBestPosition[2] - this.current.getPhase();

//            ////////////////////TEST
//            System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
//            bestPPP = getMapWithTetromino(this.current, currentBestPosition[2], currentBestPosition[0]);
//            for (int b = 20; b > 0; b--) {
//                for (int a = 1; a < 13; a++) {
//                    if (bestPPP[a][b]) {
//                        System.out.print(1);
//                    } else {
//                        System.out.print(0);
//                    }
//                }
//                System.out.println();
//            }
//            System.out.println(
//                    " \taggHeight = " + getAggHeight(bestPPP) +
//                            " \tcompleteLines = " + getCompleteLines(bestPPP) +
//                            " \tholeNum = " + getHoleNum(bestPPP) +
//                            " \tbumpiness = " + getBumpiness(bestPPP) +
//                            " \tspikiness = " + getSpikiness(bestPPP));
        }

        time1 = System.nanoTime();
        timeRamaining -= (time1 - time0) / 1000000 + 1;

        new Thread(() -> {
            for (int i = 0; i < Math.abs(xToMove); i++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
                this.move(xToMove / Math.abs(xToMove));
            }
        }, "AI-HorizonalMove Thread").start();

        new Thread(() -> {
            for (int i = 0; i < Math.abs(numToFlip); i++) {
                try {
                    Thread.sleep(10);
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

//                    TESTING
//                    for (int b = 20; b > 0; b--) {
//                        for (int a = 1; a < 13; a++) {
//                            if (mapCopy[a][b]) {
//                                System.out.print(1);
//                            } else {
//                                System.out.print(0);
//                            }
//                        }
//                        System.out.println();
//                    }
//
//                    System.out.println("x = " + chart[i - 1][phase][0] +
//                            " \ty = " + chart[i - 1][phase][1] +
//                            " \tphase = " + phase +
//                            " \taggHeight = " + getAggHeight(mapCopy) +
//                            " \tcompleteLines = " + getCompleteLines(mapCopy) +
//                            " \tholeNum = " + getHoleNum(mapCopy) +
//                            " \tbumpiness = " + getBumpiness(mapCopy) +
//                            " \tspikiness = " + chart[i - 1][phase][2]);
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
                    mapCopy[a][b] = this.map[a][b];
                }
            }
        }

        YCoordinate = 1;
        outerLoop:
        for (int a = 21; a > 0; a--) {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    if (shape[x][y] && mapCopy[xCoordinate + x][a + y - 1]) {
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
                        mapCopy[xCoordinate + x][YCoordinate + y] = true;
                    }
                }
            }
        }

        if (exceed) {
            return null;
        } else {
            return mapCopy;
        }
    }

    public int getSpikiness(boolean[][] map) {
        return (int) Math.round(51.0066 * getAggHeight(map) - 76.0666 * Math.pow(getCompleteLines(map), 2) + 35.663 * getHoleNum(map) + 18.4483 * getBumpiness(map));
    }

    public int getAggHeight(boolean[][] map) {
        int height = 0;
        for (int i = 1; i < 13; i++) {
            height += getHeight(map, i);
        }
        return height;
    }

    public int getCompleteLines(boolean[][] map) {
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

    public int getBumpiness(boolean[][] map) {
        int height = 0;
        for (int i = 2; i < 13; i++) {
            height += Math.abs(getHeight(map, i) - getHeight(map, i - 1));
        }
        return height;
    }

    @Override
    public void newTetromino(Tetromino tetromino) {
        hasUnhandledTetromino = true;
        super.newTetromino(tetromino);
    }
}