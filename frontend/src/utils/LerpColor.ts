/**
 * Linear interpolator for hex colors.
 *
 * @param a (hex color start val)
 * @param b (hex color end val)
 * @param amount (the amount to fade from a to b)
 */
export function lerpColor(a: number, b: number, amount: number) {
    const ar = a >> 16,
        ag = (a >> 8) & 0xff,
        ab = a & 0xff,
        br = b >> 16,
        bg = (b >> 8) & 0xff,
        bb = b & 0xff,
        rr = ar + amount * (br - ar),
        rg = ag + amount * (bg - ag),
        rb = ab + amount * (bb - ab);

    return (rr << 16) + (rg << 8) + (rb | 0);
}
