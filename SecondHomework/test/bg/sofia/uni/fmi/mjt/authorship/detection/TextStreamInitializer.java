package bg.sofia.uni.fmi.mjt.authorship.detection;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TextStreamInitializer {

    private static String[] knownSignatures = {
        "Agatha Christie, 4.40212537354, 0.103719383127, 0.0534892315963, 10.0836888743, 1.90662947161",
        "Alexandre Dumas, 4.38235547477, 0.049677588873, 0.0212183996175, 15.0054854981, 2.63499369483",
        "Brothers Grim, 3.96868608302, 0.0529378997714, 0.0208217283571, 22.2267197987, 3.4129614094",
        "Charles Dickens, 4.34760725241, 0.0803220950584, 0.0390662700499, 16.2613453121, 2.87721723105",
        "Douglas Adams, 4.33408042189, 0.238435104414, 0.141554321967, 13.2874354561, 1.86574870912",
        "Fyodor Dostoevsky, 4.34066732195, 0.0528571428571, 0.0233414043584, 12.8108273249, 2.16705364781",
        "James Joyce, 4.52346300961, 0.120109917189, 0.0682315429476, 10.9663296918, 1.79667373227",
        "Jane Austen, 4.41553119311, 0.0563451817574, 0.02229943808, 16.8869087498, 2.54817097682",
        "Lewis Carroll, 4.22709528497, 0.111591342227, 0.0537026953444, 16.2728740581, 2.86275565124",
        "Mark Twain, 4.33272222298, 0.117254215021, 0.0633074228159, 14.3548573631, 2.43716268311",
        "Sir Arthur Conan Doyle, 4.16808311494, 0.0822989796874, 0.0394458485444, 14.717564466, 2.2220872148",
        "William Shakespeare, 4.16216957834, 0.105602561171, 0.0575348730848, 9.34707371975, 2.24620146314" };

    private static String[] mysteryText1 = { // Author: Agatha Christie
        "A man's voice beside her made her start and turn. She had noticed the",
        "speaker more than once amongst the first-class passengers. There had",
        "been a hint of mystery about him which had appealed to her imagination.",
        "He spoke to no one. If anyone spoke to him he was quick to rebuff the",
        "overture. Also he had a nervous way of looking over his shoulder with a", "swift, suspicious glance.",
        "She noticed now that he was greatly agitated. There were beads of",
        "perspiration on his brow. He was evidently in a state of overmastering",
        "fear. And yet he did not strike her as the kind of man who would be", "afraid to meet death!"

    };

    private static String[] mysteryText = { // Brothers Grim
        "THERE was once a man who was a Jack-of-all-trades; he had served in the",
        "war, and had been brave and bold, but at the end of it he was sent about",
        "his business, with three farthings and his discharge.",

        "\"I am not going to stand this,\" said he; \"wait till I find the right man",
        "to help me, and the king shall give me all the treasures of his kingdom", "before he has done with me.\"",

        "Then, full of wrath, he went into the forest, and he saw one standing",
        "there by six trees which he had rooted up as if they had been stalks of", "corn. And he said to him,",

        "\"Will you be my man, and come along with me?\"",

        "\"All right,\" answered he; \"I must just take this bit of wood home to my",
        "father and mother.\" And taking one of the trees, he bound it round the",
        "other five, and putting the faggot on his shoulder, he carried it off;",
        "then soon coming back, he went along with his leader, who said,",

        "\"Two such as we can stand against the whole world.\"",

        "And when they had gone on a little while, they came to a huntsman who",
        "was kneeling on one knee and taking careful aim with his rifle.",

        "\"Huntsman,\" said the leader, \"what are you aiming at?\"",

        "\"Two miles from here,\" answered he, \"there sits a fly on the bough of an",
        "oak-tree, I mean to put a bullet into its left eye.\"",

        "\"Oh, come along with me,\" said the leader; \"three of us together can",
        "stand against the world.\"",

        "The huntsman was quite willing to go with him, and so they went on till",
        "they came to seven windmills, whose sails were going round briskly, and",
        "yet there was no wind blowing from any quarter, and not a leaf stirred.",

        "\"Well,\" said the leader, \"I cannot think what ails the windmills,",
        "turning without wind;\" and he went on with his followers about two miles",
        "farther, and then they came to a man sitting up in a tree, holding one",
        "nostril and blowing with the other.",

        "\"Now then,\" said the leader, \"what are you doing up there?\"",

        "\"Two miles from here,\" answered he, \"there are seven windmills; I am",
        "blowing, and they are going round.\"",

        "\"Oh, go with me,\" cried the leader, \"four of us together can stand",
        "against the world.\"",

        "So the blower got down and went with them, and after a time they came to",
        "a man standing on one leg, and the other had been taken off and was", "lying near him."

    };

    public static InputStream initKnownSignaturesStream() {
        return new ByteArrayInputStream(
                Arrays.stream(knownSignatures).collect(Collectors.joining(System.lineSeparator())).getBytes());
    }

    public static InputStream initMysteryTextStream() {
        return new ByteArrayInputStream(
                Arrays.stream(mysteryText).collect(Collectors.joining(System.lineSeparator())).getBytes());
    }

    public static InputStream initMysteryText1Stream() {
        return new ByteArrayInputStream(
                Arrays.stream(mysteryText1).collect(Collectors.joining(System.lineSeparator())).getBytes());
    }
}
