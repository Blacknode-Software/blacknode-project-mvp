const v2t = ['Low', 'Medium', 'High'] as const;

export function valueToText(value: number) {
    return v2t[value];
}
