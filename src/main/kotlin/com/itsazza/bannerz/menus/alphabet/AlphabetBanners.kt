package com.itsazza.bannerz.menus.alphabet

import org.bukkit.block.banner.PatternType

val alphabetBanners = linkedMapOf<String, BannerTemplate>().also {
    it["A"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_RIGHT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_MIDDLE)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["B"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_RIGHT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_MIDDLE)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.BACKGROUND, PatternType.CURLY_BORDER)
        pattern(ColorType.FOREGROUND, PatternType.SQUARE_TOP_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.SQUARE_BOTTOM_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["C"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["D"] = bannerTemplate{
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_RIGHT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.BACKGROUND, PatternType.CURLY_BORDER)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["E"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_MIDDLE)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["F"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_MIDDLE)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["G"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_DOWNRIGHT)
        pattern(ColorType.BACKGROUND, PatternType.DIAGONAL_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["H"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_RIGHT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_MIDDLE)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["I"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_CENTER)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["J"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.BACKGROUND, PatternType.HALF_HORIZONTAL)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_RIGHT)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["K"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_DOWNRIGHT)
        pattern(ColorType.BACKGROUND, PatternType.HALF_HORIZONTAL)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_DOWNLEFT)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["L"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["M"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.TRIANGLE_TOP)
        pattern(ColorType.BACKGROUND, PatternType.TRIANGLES_TOP)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_RIGHT)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["N"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_DOWNRIGHT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_RIGHT)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["O"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_RIGHT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["P"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.HALF_HORIZONTAL)
        pattern(ColorType.BACKGROUND, PatternType.HALF_VERTICAL)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_MIDDLE)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["Q"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_RIGHT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
        pattern(ColorType.FOREGROUND, PatternType.SQUARE_BOTTOM_RIGHT)
    }

    it["R"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.HALF_HORIZONTAL)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_DOWNRIGHT)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["S"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.BACKGROUND, PatternType.RHOMBUS_MIDDLE)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_DOWNRIGHT)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
        pattern(ColorType.BACKGROUND, PatternType.CURLY_BORDER)
    }

    it["T"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_CENTER)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["U"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_RIGHT)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["V"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.BACKGROUND, PatternType.TRIANGLE_BOTTOM)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_DOWNLEFT)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["W"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.TRIANGLE_BOTTOM)
        pattern(ColorType.BACKGROUND, PatternType.TRIANGLES_BOTTOM)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_RIGHT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["X"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.CROSS)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["Y"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.CROSS)
        pattern(ColorType.BACKGROUND, PatternType.HALF_VERTICAL_MIRROR)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_DOWNLEFT)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["Z"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_DOWNLEFT)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["1"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.SQUARE_TOP_LEFT)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_CENTER)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["2"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_DOWNLEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.BACKGROUND, PatternType.CURLY_BORDER)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["3"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_MIDDLE)
        pattern(ColorType.BACKGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_RIGHT)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["4"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.BACKGROUND, PatternType.HALF_HORIZONTAL_MIRROR)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_MIDDLE)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_RIGHT)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["5"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.BACKGROUND, PatternType.RHOMBUS_MIDDLE)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_DOWNRIGHT)
        pattern(ColorType.BACKGROUND, PatternType.CURLY_BORDER)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["6"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_RIGHT)
        pattern(ColorType.BACKGROUND, PatternType.HALF_HORIZONTAL)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_MIDDLE)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["7"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.BACKGROUND, PatternType.DIAGONAL_RIGHT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_DOWNLEFT)
        pattern(ColorType.FOREGROUND, PatternType.SQUARE_BOTTOM_LEFT)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["8"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_RIGHT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_MIDDLE)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["9"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_LEFT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.BACKGROUND, PatternType.HALF_HORIZONTAL_MIRROR)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_MIDDLE)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_RIGHT)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }

    it["0"] = bannerTemplate {
        pattern(ColorType.FOREGROUND, PatternType.HALF_VERTICAL)
        pattern(ColorType.FOREGROUND, PatternType.HALF_VERTICAL_MIRROR)
        pattern(ColorType.BACKGROUND, PatternType.STRIPE_CENTER)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_TOP)
        pattern(ColorType.FOREGROUND, PatternType.STRIPE_BOTTOM)
        pattern(ColorType.BACKGROUND, PatternType.BORDER)
    }
}