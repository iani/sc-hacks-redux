a = Actor2();
b = a.action;
b.isPolling;
b addAction: b.simplePost;
b.removeAction;
b.stopPolling;
b.poll;
