            - $P(Sprinkler|Rained) = 0.10$
            - $P(\neg Sprinkler|Rained) = 0.90$
            - $P(Wet|Sprinkler) = 0.70$
            - $P(\neg Wet|Sprinkler) = 0.30$
            - $P(Wet|Rained) = 0.95$
            - $P(\neg Wet|Rained) = 0.05$

P(S|~R) = P(~R|S)

0.0 + (0.9 * 0.4 * 0.8) + (0.8 * 0.99 * 0.2) + (0.99 * 0.01 * 0.20)
val x1 = (0.9 * 0.4 * 0.8)
val x2 = (0.8 * 0.99 * 0.2)
val x3 = (0.99 * 0.01 * 0.20)
x1 + x2 + x3
0.0 + 0.288 + 0.158 + 0.002