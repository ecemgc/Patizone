import {
  Avatar,
  Box,
  Divider,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  Typography
} from '@mui/material';
import { useQuery } from '@tanstack/react-query';
import UserService from '../../services/UserService';
import { useAuthStore } from '../../store/useAuthStore';
import Spinner from '../Spinner';

export default function ChatUserList({ onSelectUser, selectedUserId }) {
  const { user: currentUser } = useAuthStore();
  const { data, isFetching } = useQuery({
    queryKey: ['userList'],
    queryFn: () => UserService.getAll()
  });
  return (
    <Spinner active={isFetching}>
      <Box sx={{ width: 300, borderRight: '1px solid #ddd', height: '100vh', overflowY: 'auto' }}>
        <Typography variant="h6" sx={{ p: 2 }}>
          Users
        </Typography>
        <Divider />
        <List>
          {data
            ?.filter((user) => user.id !== currentUser?.id)
            .map((user) => (
              <ListItem
                key={user.id}
                button
                selected={user.id === selectedUserId}
                onClick={() => onSelectUser(user)}>
                <ListItemAvatar>
                  <Avatar src={user.imageUrl || undefined}>
                    {!user.imageUrl && user.firstName[0]}
                  </Avatar>
                </ListItemAvatar>
                <ListItemText primary={user.firstName} secondary={''} />

                <Box
                  width={10}
                  height={10}
                  borderRadius={5}
                  bgcolor={user.isOnline ? 'green' : 'red'}
                  ml={1}
                />
              </ListItem>
            ))}
        </List>
      </Box>
    </Spinner>
  );
}
