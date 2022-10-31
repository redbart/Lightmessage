package net.lightmessage.common.packets;

import java.util.Objects;

public record ConversationStatus(int conversationId, int sequenceId) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversationStatus that = (ConversationStatus) o;
        return conversationId == that.conversationId && sequenceId == that.sequenceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(conversationId, sequenceId);
    }

    @Override
    public String toString() {
        return "ConversationStatus{" +
                "conversationId=" + conversationId +
                ", sequenceId=" + sequenceId +
                '}';
    }
}
