import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import PersonIcon from '@mui/icons-material/Person';
import {
  Avatar,
  Box,
  Card,
  CardActions,
  CardContent,
  CardHeader,
  CardMedia,
  Chip,
  Collapse,
  Divider,
  Fade,
  IconButton,
  Stack,
  Tooltip,
  Typography
} from '@mui/material';
import { styled } from '@mui/material/styles';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import dayjs from '../../util/dayjs';

const ExpandMore = styled((props) => {
  const { expand, ...other } = props;
  return <IconButton {...other} />;
})(({ theme, expand }) => ({
  marginLeft: 'auto',
  transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
  transition: theme.transitions.create('transform', {
    duration: theme.transitions.duration.shortest
  })
}));

export default function Ad({ ad }) {
  const [expanded, setExpanded] = useState(false);
  const navigate = useNavigate();

  const handleExpandClick = () => setExpanded(!expanded);
  const handleOwnerClick = () => navigate(`/user/${ad.owner.id}`);

  return (
    <Card
      sx={{
        width: '100%',
        height: '100%',
        display: 'flex',
        flexDirection: 'column',
        transition: 'box-shadow 0.3s ease',
        '&:hover': { boxShadow: 6 }
      }}>
      <CardHeader
        avatar={
          <Tooltip title="View profile" arrow>
            <IconButton onClick={handleOwnerClick}>
              <Avatar src={ad.owner.imageUrl || undefined}>
                {!ad.owner.imageUrl && ad.owner.firstName[0]}
              </Avatar>
            </IconButton>
          </Tooltip>
        }
        title={ad.title}
        subheader={dayjs(ad.createdAt).fromNow()}
      />

      {ad.imageUrl && (
        <CardMedia
          component="img"
          height="180"
          image={ad.imageUrl}
          alt={ad.title}
          sx={{ objectFit: 'cover' }}
        />
      )}

      <CardContent sx={{ flexGrow: 1 }}>
        <Typography
          variant="body2"
          sx={{
            color: 'text.secondary',
            overflow: 'hidden',
            textOverflow: 'ellipsis',
            display: '-webkit-box',
            WebkitLineClamp: 3,
            WebkitBoxOrient: 'vertical',
            mb: 1
          }}>
          {ad.description}
        </Typography>

        <Divider sx={{ my: 1 }} />

        <Stack spacing={1}>
          <Info label="Animal Type" value={ad.animalType} />
          <Info label="Animal Name" value={ad.animalName} />
          <Info label="Animal Breed" value={ad.animalBreed} />
          <Info label="Animal Age" value={ad.animalAge ? `${ad.animalAge} years` : '-'} />
          <Info label="Ad Type" value={ad.adType?.text} />
          <Info label="End Date" value={dayjs(ad.endDate).format('DD MMM YYYY')} />
        </Stack>

        <Divider sx={{ my: 1 }} />

        <Box
          onClick={handleOwnerClick}
          sx={{
            display: 'flex',
            alignItems: 'center',
            gap: 1,
            cursor: 'pointer',
            mt: 1,
            '&:hover .owner-name': { textDecoration: 'underline' }
          }}>
          <PersonIcon fontSize="small" color="action" />
          <Typography
            variant="body2"
            className="owner-name !cursor-pointer"
            sx={{ fontWeight: 500, color: 'text.primary', cursor: 'pointer' }}>
            {ad.owner.firstName} {ad.owner.lastName}
          </Typography>
        </Box>

        {ad.owner.isOnline && <Chip label="Online" size="small" color="success" sx={{ mt: 1 }} />}

        <Typography variant="caption" sx={{ mt: 1 }} color="text.secondary">
          Created: {dayjs(ad.createdAt).format('DD MMM YYYY HH:mm')}
        </Typography>
      </CardContent>

      <CardActions disableSpacing sx={{ justifyContent: 'flex-end' }}>
        <ExpandMore
          expand={expanded}
          onClick={handleExpandClick}
          aria-expanded={expanded}
          aria-label="show more">
          <ExpandMoreIcon />
        </ExpandMore>
      </CardActions>

      <Collapse in={expanded} timeout="auto" unmountOnExit>
        <Fade in={expanded} timeout={500}>
          <Box sx={{ p: 2, bgcolor: '#f9f9f9', borderTop: '1px solid #ddd' }}>
            <Typography variant="h6" sx={{ mb: 1 }}>
              More Info
            </Typography>

            <Box
              sx={{
                bgcolor: 'white',
                p: 2,
                borderRadius: 2,
                boxShadow: 1,
                mb: 2
              }}>
              <Typography variant="subtitle2" sx={{ color: 'text.secondary' }}>
                Description
              </Typography>
              <Typography variant="body2">{ad.fullDescription || ad.description}</Typography>
            </Box>

            <Box
              sx={{
                bgcolor: 'white',
                p: 2,
                borderRadius: 2,
                boxShadow: 1
              }}>
              <Typography variant="subtitle2" sx={{ color: 'text.secondary' }}>
                Address
              </Typography>
              <Typography variant="body2">{ad.address || '-'}</Typography>
            </Box>
          </Box>
        </Fade>
      </Collapse>
    </Card>
  );
}

function Info({ label, value }) {
  return (
    <Typography variant="body2">
      <strong>{label}:</strong> {value || '-'}
    </Typography>
  );
}
