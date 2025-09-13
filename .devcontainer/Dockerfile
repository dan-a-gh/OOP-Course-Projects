FROM quay.io/fedora/fedora:42-x86_64

# If this username changes to anything but `dev`, make sure to change the workspaceFolder in devcontainer.json to match
ARG USERNAME=dev
ARG USER_UID=1000
ARG USER_GID=$USER_UID

# Create the user
RUN groupadd --gid $USER_GID $USERNAME \
    && useradd --uid $USER_UID --gid $USER_GID -m $USERNAME \
    #
    # [Optional] Add sudo support. Omit if you don't need to install software after connecting.
    && dnf install -y sudo \
    && echo "$USERNAME ALL=(ALL) NOPASSWD:ALL" > /etc/sudoers.d/$USERNAME \
    && chmod 0440 /etc/sudoers.d/$USERNAME

# Switch to appropriate user
USER ${USERNAME}

RUN SNIPPET="export PROMPT_COMMAND='history -a' && export HISTFILE=/home/${USERNAME}/commandhistory/.bash_history" \
    && mkdir /home/${USERNAME}/commandhistory \
    && touch /home/${USERNAME}/commandhistory/.bash_history \
    && chown -R $USERNAME /home/${USERNAME}/commandhistory \
    && echo "$SNIPPET" >> "/home/$USERNAME/.bashrc"

RUN sudo dnf makecache -y
RUN sudo dnf install -y \
    unzip \
    zip \
    git \
    which

# Python installers
COPY --from=ghcr.io/astral-sh/uv:latest /uv /uvx /bin/
RUN uv tool install commitizen

# Java SDK installers
RUN curl -s "https://get.sdkman.io" | bash
RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && \
    sdk install gradle && \
    sdk install java 24.0.2-tem
