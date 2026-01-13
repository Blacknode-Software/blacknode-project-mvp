import type { UnixTimestamp } from '@/utils';

export interface Invite {
    id: string;
    email: string;
    token: string;
    revoked: boolean;
    expired: boolean;
    expiresAt: UnixTimestamp;
    claimed: boolean;
    claimedBy: string;
    claimedAt: UnixTimestamp;
    status: string;
    message: string;
}
