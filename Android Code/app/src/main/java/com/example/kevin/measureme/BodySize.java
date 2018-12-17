package com.example.kevin.measureme;

/**
 * Created by Kevin on 7/2/2018.
 */

public class BodySize {

    private static final double ERROR_RATE_CHEST_MALE = 10.0;
    private static final double ERROR_RATE_WAIST_MALE = 4.0;
    private static final double ERROR_RATE_CHEST_FEMALE = 8.5;
    private static final double ERROR_RATE_WAIST_FEMALE = -1.5;

    private int gender;
    private int[] width;
    private int[] thick;
    private int[] male_chest_cloth_size;
    private int[] male_waist_cloth_size;
    private int[] female_chest_cloth_size;
    private int[] female_waist_cloth_size;

    protected int shoulder_width;
    protected double chest_round, waist_round;

    protected String size;

    public BodySize(int shoulder_width, int[] width, int[] thick, int gender, //gender, 1: Male, 2: Female
                    int[] male_chest_cloth_size, int[] male_waist_cloth_size,
                    int[] female_chest_cloth_size, int[] female_waist_cloth_size) {
        this.shoulder_width = shoulder_width;
        this.width = width;
        this.thick = thick;
        this.gender = gender;
        this.male_chest_cloth_size = male_chest_cloth_size;
        this.male_waist_cloth_size = male_waist_cloth_size;
        this.female_chest_cloth_size = female_chest_cloth_size;
        this.female_waist_cloth_size = female_waist_cloth_size;
    }

    public void measureSize() {
        if(gender==1) {
            chest_round = (0.5 * Math.PI * (width[0] + thick[0])) + ERROR_RATE_CHEST_MALE;
            waist_round = (0.5 * Math.PI * (width[1] + thick[1])) + ERROR_RATE_WAIST_MALE;
        }  else if(gender==2) {
            chest_round = (0.5 * Math.PI * (width[0] + thick[0])) + ERROR_RATE_CHEST_FEMALE;
            waist_round = (0.5 * Math.PI * (width[1] + thick[1])) + ERROR_RATE_WAIST_FEMALE;
        }
        checkSize();
    }

    public void checkSize() {
        /* 0: Size not available
           1: Size XS
           2: Size S
           3: Size M
           4: Size L
           5: Size XL
           6: Size XXL */

        int sizeTempChest = 0;
        int sizeTempWaist = 0;
        int sizeTempAll = 0;

        if(gender==1) { // Male
            // Chest
            if((chest_round >= male_chest_cloth_size[0]) && (chest_round <= male_chest_cloth_size[1])) {
                sizeTempChest=1;
            } else if((chest_round >= male_chest_cloth_size[2]) && (chest_round <= male_chest_cloth_size[3])) {
                sizeTempChest=2;
            } else if((chest_round >= male_chest_cloth_size[4]) && (chest_round <= male_chest_cloth_size[5])) {
                sizeTempChest=3;
            } else if((chest_round >= male_chest_cloth_size[6]) && (chest_round <= male_chest_cloth_size[7])) {
                sizeTempChest=4;
            } else if((chest_round >= male_chest_cloth_size[8]) && (chest_round <= male_chest_cloth_size[9])) {
                sizeTempChest=5;
            } else if((chest_round >= male_chest_cloth_size[10]) && (chest_round <= male_chest_cloth_size[11])) {
                sizeTempChest=6;
            }

            // Waist
            if((waist_round >= male_waist_cloth_size[0]) && (waist_round <= male_waist_cloth_size[1])) {
                sizeTempWaist=1;
            } else if((waist_round >= male_waist_cloth_size[2]) && (waist_round <= male_waist_cloth_size[3])) {
                sizeTempWaist=2;
            } else if((waist_round >= male_waist_cloth_size[4]) && (waist_round <= male_waist_cloth_size[5])) {
                sizeTempWaist=3;
            } else if((waist_round >= male_waist_cloth_size[6]) && (waist_round <= male_waist_cloth_size[7])) {
                sizeTempWaist=4;
            } else if((waist_round >= male_waist_cloth_size[8]) && (waist_round <= male_waist_cloth_size[9])) {
                sizeTempWaist=5;
            } else if((waist_round >= male_waist_cloth_size[10]) && (waist_round <= male_waist_cloth_size[11])) {
                sizeTempWaist=6;
            }

        } else if(gender==2) { // Female
            // Chest
            if((chest_round >= female_chest_cloth_size[0]) && (chest_round <= female_chest_cloth_size[1])) {
                sizeTempChest=1;
            } else if((chest_round >= female_chest_cloth_size[2]) && (chest_round <= female_chest_cloth_size[3])) {
                sizeTempChest=2;
            } else if((chest_round >= female_chest_cloth_size[4]) && (chest_round <= female_chest_cloth_size[5])) {
                sizeTempChest=3;
            } else if((chest_round >= female_chest_cloth_size[6]) && (chest_round <= female_chest_cloth_size[7])) {
                sizeTempChest=4;
            } else if((chest_round >= female_chest_cloth_size[8]) && (chest_round <= female_chest_cloth_size[9])) {
                sizeTempChest=5;
            } else if((chest_round >= female_chest_cloth_size[10]) && (chest_round <= female_chest_cloth_size[11])) {
                sizeTempChest=6;
            }

            // Waist
            if((waist_round >= female_waist_cloth_size[0]) && (waist_round <= female_waist_cloth_size[1])) {
                sizeTempWaist=1;
            } else if((waist_round >= female_waist_cloth_size[2]) && (waist_round <= female_waist_cloth_size[3])) {
                sizeTempWaist=2;
            } else if((waist_round >= female_waist_cloth_size[4]) && (waist_round <= female_waist_cloth_size[5])) {
                sizeTempWaist=3;
            } else if((waist_round >= female_waist_cloth_size[6]) && (waist_round <= female_waist_cloth_size[7])) {
                sizeTempWaist=4;
            } else if((waist_round >= female_waist_cloth_size[8]) && (waist_round <= female_waist_cloth_size[9])) {
                sizeTempWaist=5;
            } else if((waist_round >= female_waist_cloth_size[10]) && (waist_round <= female_waist_cloth_size[11])) {
                sizeTempWaist=6;
            }
        }

        sizeTempAll=Math.max(sizeTempChest, sizeTempWaist);

        if(sizeTempAll==0) {
            size = "Not Available";
        } else if(sizeTempAll==1) {
            size = "XS";
        } else if(sizeTempAll==2) {
            size = "S";
        } else if(sizeTempAll==3) {
            size = "M";
        } else if(sizeTempAll==4) {
            size = "L";
        } else if(sizeTempAll==5) {
            size = "XL";
        } else if(sizeTempAll==6) {
            size = "XXL";
        }
    }

    public int getShoulderWidth() {
        return shoulder_width;
    }

    public double getChestRound() {
        return Math.round(chest_round);
    }

    public double getWaistRound() {
        return Math.round(waist_round);
    }

    public String getClothSize() {
        return size;
    }
}