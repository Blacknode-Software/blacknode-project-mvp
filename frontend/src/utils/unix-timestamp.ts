export default class UnixTimestamp {
    private static UNIX_TO_DATE = 1000;

    public readonly unixTimestamp;

    public constructor(unixTimestamp: number) {
        this.unixTimestamp = unixTimestamp;
    }

    public getDateTimestamp() {
        return this.unixTimestamp * UnixTimestamp.UNIX_TO_DATE;
    }

    public getDate() {
        return new Date(this.getDateTimestamp());
    }
}
