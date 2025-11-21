export default class UnixTimestamp {
    private static UNIX_TO_DATE = 1000;

    private readonly unixValue;

    public constructor(unixTimestamp: number) {
        this.unixValue = unixTimestamp;
    }

    public getUnixTimestamp() {
        return this.unixValue;
    }

    public getDateTimestamp() {
        return this.unixValue * UnixTimestamp.UNIX_TO_DATE;
    }

    public getDate() {
        return new Date(this.getDateTimestamp());
    }
}
