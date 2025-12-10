export interface ApiChannel {
    name: string;
    uuid: string;
}

export interface UiChannel {
    name: string;
    uuid: string;
}

export function channelUi(apiChannel: ApiChannel): UiChannel {
    return {
        name: apiChannel.name,
        uuid: apiChannel.uuid,
    };
}

export function channelApi(uiChannel: UiChannel): ApiChannel {
    return {
        name: uiChannel.name,
        uuid: uiChannel.uuid,
    };
}
