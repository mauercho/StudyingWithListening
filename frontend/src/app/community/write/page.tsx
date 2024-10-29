import CommunityPostEditor from '@/app/community/_components/CommunityPostEditor'
import CommunityPostLayout from '@/app/community/_components/CommunityPostLayout'

export default function PostCreate() {
  return (
    <CommunityPostLayout>
      <div className="flex justify-center mt-32 pb-10">
        <CommunityPostEditor />
      </div>
    </CommunityPostLayout>
  )
}
