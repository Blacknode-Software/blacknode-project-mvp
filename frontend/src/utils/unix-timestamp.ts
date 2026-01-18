import dayjs from 'dayjs';

export default class UnixTimestamp {
    private static UNIX_TO_DATE = 1000;
    private static DATE_TO_UNIX = 1 / 1000;

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

    public static fromInputString(str: string) {
        const ms = new Date(str).getTime();

        return new UnixTimestamp(ms * UnixTimestamp.DATE_TO_UNIX);
    }

    public toInputString() {
        return dayjs(this.getDate()).format('YYYY-MM-DDTHH:mm');
    }
}
