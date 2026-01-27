package betteripfilter;

public enum DenyReason {
    NOT_WHITELISTED,
    RATE_LIMIT,
    FAILSAFE,
    PROXY_NOT_TRUSTED
}
