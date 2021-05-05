package net.runelite.client.plugins.moonemotes;

import com.google.common.collect.ImmutableMap;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.util.Map;

enum MoonEmotes {
    MOONGUMS("moon2Gums"),
    MOONT("moon2T"),
    MOONSPY("moon2Spy"),
    MOONE("moon2E"),
    MOONM("moon2M"),
    MOONMM("moon2Mm"),
    MOONMD("moon2Md"),
    MOONH("moon2H"),
    MOONCD("moon2Cd"),
    MOONO("moon2O"),
    MOONSP("moon2Sp"),
    MOONPH("moon2Ph"),
    MOONG("moon2G"),
    MOONAY("moon2Ay"),
    MOONME("moon2Me"),
    MOONWUT("moon2Wut"),
    MOONA("moon2A"),
    MOONN("moon2N"),
    MOONDOIT("moon2Doit"),
    MOONSECRETEMOTE("moon2Secretemote"),
    MOOND("moon2D"),
    MOONC("moon2C"),
    MOONCOFFEE("moon2Coffee"),
    MOONY("moon2Y"),
    MOONB("moon2B"),
    MOONBRAIN("moon2Brain"),
    MOONSMUG("moon2Smug"),
    MOONEZ("moon2Ez"),
    MOONCR("moon2Cr"),
    MOONPREGARIO("moon2Pregario"),
    MOONLOLE("moon2Lole"),
    MOONDODGE("moon2Dodge"),
    MOONJR("moon2Jr"),
    MOONDEV("moon2Dev"),
    MOONSH("moon2Sh"),
    MOONS("moon2S"),
    MOONJIMBO("moon2Jimbo"),
    MOONGN("moon2Gn"),
    MOONCV("moon2Cv"),
    MOONEE("moon2Ee"),
    MOONLL("moon2Ll"),
    MOONGUNCH("moon2Gunch"),
    MOONSNIFF("moon2Sniff"),
    MOONW("moon2W"),
    MOONONE("moon21"),
    MOONTWO("moon22"),
    MOONTHREE("moon23"),
    MOONFOUR("moon24"),
    MOONL("moon2L"),
    MOONLEWD("moon2Lewd"),
    MOONSOOFER("moon2Soofer"),
    MOONPOG("moon2Pog"),
    MOONCUTE("moon2Cute"),
    MOONLENNY("moon2Lenny"),
    MOONWOW("moon2Wow"),
    MOONSL("moon2Sl"),
    MOONSMERG("moon2Smerg"),
    MOONBED("moon2Bed"),
    MOONSUFFER("moon2Suffer"),
    MOONHUH("moon2Huh"),
    MOONDAB("moon2Dab"),
    MOONSMAG("moon2Smag"),
    MOONYE("moon2Ye"),
    MOONHEY("moon2Hey"),
    MOONOLDWOW("moon2Oldwow"),
    MOONSMEG("moon2Smeg"),
    ;

    private static final Map<String,MoonEmotes> moonMap;

    private final String trigger;

    static
    {
        ImmutableMap.Builder<String, MoonEmotes> builder = new ImmutableMap.Builder<>();

        for (final MoonEmotes emoji : values())
        {
            builder.put(emoji.trigger, emoji);
        }

        moonMap = builder.build();
    }

    MoonEmotes(String trigger){this.trigger=trigger;}

    BufferedImage loadImage(){return ImageUtil.loadImageResource(getClass(),this.name().toLowerCase()+".png");}


    static MoonEmotes getEmote(String trigger){ return moonMap.get(trigger);}
}
