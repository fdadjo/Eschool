export const enum MessageType {
    COMMA = 'COMMA'
}

export interface IMessage {
    id?: number;
    title?: string;
    content?: string;
    contentType?: string;
    type?: MessageType;
}

export class Message implements IMessage {
    constructor(
        public id?: number,
        public title?: string,
        public content?: string,
        public contentType?: string,
        public type?: MessageType
    ) {}
}
