package org.gajaba.rule.compiler;

public class TokenType {
    private int tokenId;
    private String tokenText;

    public TokenType(int tokenId, String tokenText) {
        this.tokenId = tokenId;
        this.tokenText = tokenText;
    }

    public int getTokenId() {
        return tokenId;
    }

    public String getTokenText() {
        return tokenText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenType tokenType = (TokenType) o;

        return tokenId == tokenType.tokenId &&
                !(tokenText != null ? !tokenText.equals(tokenType.tokenText)
                        : tokenType.tokenText != null);

    }

    @Override
    public int hashCode() {
        int result = tokenId;
        result = 31 * result + (tokenText != null ? tokenText.hashCode() : 0);
        return result;
    }
}
