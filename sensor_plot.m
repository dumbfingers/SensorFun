figure;
%hold on;
x = accelerometer(104:end, 1);
% convert the x (time) value from unix time to matlab time
x = (x / 86400 / 1000) + datenum(1970,1,1);
y1 = accelerometer(104:end, 2);
y2 = accelerometer(104:end, 3);
y3 = accelerometer(104:end, 4)
plot(x, y1, x, y2, x, y3);
%xlim([min(accelerometer(:, 1)) max(accelerometer(:, 1))]);
datetick('x','yyyy-mm-dd HH:MM:SS.FFF');
axis 'auto x';
hleg1 = legend('x', 'y', 'z');
grid on;
